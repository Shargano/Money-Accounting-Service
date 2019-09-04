<template>
    <div style="float: left;">
        <button type="button" @click="show" class="addListItem">
            <svg version="1.1" id="Capa_1" xmlns="http://www.w3.org/2000/svg"
                 x="0px" y="0px"
                 width="50px" height="50px" viewBox="0 0 510 510" style="enable-background:new 0 0 510 510;"
                 xml:space="preserve">
        <g>
            <g id="add-circle-outline">
                <path d="M280.5,127.5h-51v102h-102v51h102v102h51v-102h102v-51h-102V127.5z M255,0C114.75,0,0,114.75,0,255s114.75,255,255,255
                s255-114.75,255-255S395.25,0,255,0z M255,459c-112.2,0-204-91.8-204-204S142.8,51,255,51s204,91.8,204,204S367.2,459,255,459z"
                      data-original="#22ea00" class="active-path" fill="#19bd9a"/>
                </g>
            </g>
        </svg>
        </button>

        <div class="modal fade" id="modalForm" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Wallet</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body text-center">
                        <div class="invalid invalidname"></div>
                        <input type="text" placeholder="Enter name" v-model="name"><br>
                        <div class="invalid invalidmoneyCount"></div>
                        <input type="text" placeholder="Enter money count" v-model="moneyCount"><br>
                        <select name="currency" id="">
                            <option>USD</option>
                        </select>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary closeButton" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary saveButton" @click="save">Save</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "WalletForm",
        props: ['wallets', 'walletAttr'],
        data: function () {
            return {
                id: '',
                name: '',
                moneyCount: ''
            }
        },
        watch: {
            walletAttr: function (newVal, oldVal) {
                this.id = newVal.id;
                this.name = newVal.name;
                this.moneyCount = newVal.moneyCount;
            }
        },
        methods: {
            show: function () {
                $('.invalidname').text('');
                $('.invalidmoneyCount').text('');
                $('#modalForm').modal('show');
            },
            save: function () {
                var wallet = {name: this.name, moneyCount: this.moneyCount};
                if (this.id) {
                    this.$resource('/api/v1/wallet{/id}').update({id: this.id}, wallet).then(result =>
                        result.json().then(data => {
                            var index = this.wallets.indexOf(data.id);
                            this.wallets.splice(index, 1, data);
                            this.name = '';
                            this.moneyCount = '';
                            this.id = '';
                            $('#modalForm').modal('hide');
                        })
                    ).catch(error => {
                        $.each(error.body.errors, function (index, value) {
                            $('.invalid' + value.field).text(value.message);
                        });
                    })
                } else {
                    this.$resource('/api/v1/wallet{/id}').save({}, wallet).then(result =>
                        result.json().then(data => {
                            this.wallets.push(data);
                            this.name = '';
                            this.moneyCount = '';
                            $('#modalForm').modal('hide');
                        })
                    ).catch(error => {
                        $.each(error.body.errors, function (index, value) {
                            $('.invalid' + value.field).text(value.message);
                        });
                    })
                }
            }
        }
    }
</script>

<style scoped>
    .addListItem {
        margin: 37px;
        display: block;
        width: 300px;
        height: 250px;
        border-radius: 8px;
        border: 1px solid #19bd9a;
    }

    #modalForm .modal-title {
        color: #000;
    }

    #modalForm input, select {
        margin-bottom: 10px;
        border-radius: 5px;
        border: 1px solid silver;
    }

    #modalForm input:active, select:active {
        margin-bottom: 10px;
        border-radius: 5px;
        border: 1px solid #19bd9a;
    }

    #modalForm .closeButton {
        margin: 0 auto;
        background-color: darkred;
        color: #fff;
    }

    #modalForm .saveButton {
        margin: 0 auto;
        background-color: #19bd9a;
        color: #fff;
    }

    select {
        width: 190px;
        height: 32px;
    }

</style>