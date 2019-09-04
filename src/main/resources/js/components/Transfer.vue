<template>
    <div class="col-md-12 text-center">
        <button type="button" @click="show" class="makeReport">
            <h4>Make new transfer</h4>
        </button>

        <div class="modal fade" id="modalTransfer" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Transfer</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body text-center">
                        <div>Choose source wallet</div>
                        <div class="invalid invalidwalletFrom"></div>
                        <select name="walletFrom" v-model="walletFrom">
                            <option v-for="wallet in wallets">{{wallet.name}}</option>
                        </select>
                        <div>Choose goal wallet</div>
                        <div class="invalid invalidwalletTo"></div>
                        <select name="walletFrom" v-model="walletTo">
                            <option v-for="wallet in wallets">{{wallet.name}}</option>
                        </select>
                        <div class="invalid invalidmoneyCount"></div>
                        <input type="text" placeholder="Enter money count" v-model="moneyCount"><br>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary closeButton" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary saveButton" @click="save">Make transfer</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "Transfer",
        props: ['wallets'],
        data: function () {
            return {
                id: '',
                moneyCount: '',
                walletFrom: '',
                walletTo: ''
            }
        },
        methods: {
            show: function () {
                $('.invalidwalletFrom').text('');
                $('.invalidmoneyCount').text('');
                $('.invalidwalletTo').text('');
                $('#modalTransfer').modal('show');
            },
            save: function () {
                var request = {walletFrom: '', walletTo: '', moneyCount: this.moneyCount};

                this.wallets.forEach(item => {
                    if (item.name === this.walletFrom) {
                        request.walletFrom = item.id;
                        item.moneyCount = parseInt(item.moneyCount) - parseInt(request.moneyCount);
                    }
                    if (item.name === this.walletTo) {
                        request.walletTo = item.id;
                        item.moneyCount = parseInt(item.moneyCount) + parseInt(request.moneyCount);
                    }
                });

                this.$resource('/api/v1/wallet/transfer').save({}, request).then(result =>
                    result.json().then(data => {
                        this.walletTo = '';
                        this.walletFrom = '';
                        this.moneyCount = '';
                        $('#modalTransfer').modal('hide');
                    })
                ).catch(error => {
                    $.each(error.body.errors, function (index, value) {
                        $('.invalid' + value.field).text(value.message);
                    });
                })
            }
        }
    }
</script>

<style scoped>
    .makeReport {
        margin: 10px;
        background-color: #19bd9a;
        min-width: 75%;
        height: 80px;
        border-radius: 8px;
        border: 1px solid #19bd9a;
    }

    .makeReport h4 {
        color: #fff;
    }

    #modalTransfer .modal-title {
        color: #000;
    }

    #modalTransfer input, select {
        width: 200px;
        height: 30px;
        margin: 10px auto;
        border-radius: 5px;
        border: 1px solid silver;
    }

    #modalTransfer input:active, select:active {
        margin-bottom: 10px;
        border-radius: 5px;
        border: 1px solid #19bd9a;
    }

    #modalTransfer .closeButton {
        margin: 0 auto;
        background-color: darkred;
        color: #fff;
    }

    #modalTransfer .saveButton {
        margin: 0 auto;
        background-color: #19bd9a;
        color: #fff;
    }
</style>