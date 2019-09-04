<template>
    <div>
        <button type="button" @click="show('#modalLimit')" class="createLimit">Create limit</button>

        <div class="modal fade" id="modalLimit" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="lossLabel">Limit</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body text-center">
                        <div>Choose limit begin date</div>
                        <div class="invalid invaliddateFrom"></div>
                        <input type="date" placeholder="Enter loss name" v-model="dateFrom"><br>
                        <div>Choose limit end date</div>
                        <div class="invalid invaliddateTo"></div>
                        <input type="date" placeholder="Enter loss name" v-model="dateTo"><br>
                        <div class="invalid invalidmoneyCount"></div>
                        <input type="text" placeholder="Enter money count" v-model="moneyCount"><br>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary closeButton" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary saveButton" @click="saveLimit">Save</button>
                    </div>
                </div>
            </div>
        </div>

    </div>
</template>

<script>
    export default {
        name: "LimitForm",
        props: ['limits', 'walletId'],
        data: function () {
            return {
                id: '',
                dateTo: '',
                dateFrom: '',
                moneyCount: ''
            }
        },
        methods: {

            show(modalName) {
                $('.invaliddateTo').text('');
                $('.invaliddateFrom').text('');
                $('.invalidmoneyCount').text('');
                $(modalName).modal('show');
            },

            saveLimit() {
                var limit = {dateTo: this.dateTo, dateFrom: this.dateFrom, moneyCount: this.moneyCount};

                this.$resource('/api/v1/wallet{/id}/limit').save({id: this.walletId}, limit).then(result =>
                    result.json().then(data => {
                        this.dateTo = '';
                        this.dateFrom = '';
                        this.moneyCount = '';
                        $('#modalLimit').modal('hide');
                        var lim = data.limits[data.limits.length - 1];
                        lim.dateFrom = lim.dateFrom.split("T")[0];
                        lim.dateTo = lim.dateTo.split("T")[0];
                        this.limits.push(lim);
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
    .createLimit {
        margin: 20px 20px;
        transition: all 1s;
    }

    .createLimit:hover {
        margin: 20px 20px;
        transition: all 1s;
        color: #19bd9a;
        border: 1px solid #19bd9a;
    }

    .modal .modal-title {
        color: #000;
    }

    .modal input {
        margin-bottom: 10px;
        border-radius: 5px;
        border: 1px solid silver;
    }

    .modal input:active {
        margin-bottom: 10px;
        border-radius: 5px;
        border: 1px solid #19bd9a;
    }

    .modal .closeButton {
        margin: 0 auto;
        background-color: darkred;
        color: #fff;
    }

    .modal .saveButton {
        margin: 0 auto;
        background-color: #19bd9a;
        color: #fff;
    }

</style>