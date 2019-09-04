<template>
    <div>
        <button type="button" @click="show('#modalProfit')" class="createPayment">Create profit</button>
        <button type="button" @click="show('#modalLoss')" class="createPayment">Create loss</button>

        <div class="modal fade" id="modalProfit" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="profitLabel">Profit</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body text-center">
                        <div class="invalid invalidname"></div>
                        <input type="text" placeholder="Enter profit name" v-model="name"><br>
                        <div class="invalid invalidmoneyCount"></div>
                        <input type="text" placeholder="Enter money count" v-model="moneyCount"><br>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary closeButton" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary saveButton" @click="saveProfit">Save</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="modalLoss" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="lossLabel">Loss</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body text-center">
                        <div class="invalid invalidname"></div>
                        <input type="text" placeholder="Enter loss name" v-model="name"><br>
                        <div class="invalid invalidmoneyCount"></div>
                        <input type="text" placeholder="Enter money count" v-model="moneyCount"><br>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary closeButton" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary saveButton" @click="saveLoss">Save</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "PaymentForm",
        props: ['payments', 'paymentAttr', 'walletId'],
        data: function () {
            return {
                id: '',
                goalId: '',
                name: '',
                moneyCount: ''
            }
        },
        watch: {
            paymentAttr: function (newVal, oldVal) {
                this.id = newVal.id;
                this.goalId = newVal.goalId;
                this.moneyCount = newVal.moneyCount;
                this.type = newVal.type;
            }
        },
        methods: {

            refresh: function () {
                this.$http.get('/api/v1/wallet/' + this.walletId + '/payment').then(result =>
                    result.json().then(data => {
                            data.forEach(pay => {
                                pay.dateTime = pay.dateTime.split("T")[0] + ' '
                                    + pay.dateTime.split("T")[1].split("+")[0].split(":")[0] + ':'
                                    + pay.dateTime.split("T")[1].split("+")[0].split(":")[1];
                                if (pay.type === 'TRANSFER_FROM')
                                    pay.moneyCount = '-' + pay.moneyCount;
                                if (pay.type === 'TRANSFER_TO')
                                    pay.moneyCount = '+' + pay.moneyCount;
                                this.payments.push(pay);
                            });
                        }
                    )
                )
            },

            show: function (modalName) {
                $('.invalidname').text('');
                $('.invalidmoneyCount').text('');
                $(modalName).modal('show');
            },

            saveProfit: function () {
                var profit = {walletId: this.walletId, name: this.name, moneyCount: this.moneyCount};

                if (this.id) {
                    this.$resource('/api/v1/profit{/id}').update({id: this.goalId}, profit).then(result =>
                        result.json().then(data => {
                            this.name = '';
                            this.moneyCount = '';
                            this.id = '';
                            $('#modalProfit').modal('hide');
                            this.payments.splice(0, this.payments.length);
                            this.refresh();
                        })
                    ).catch(error => {
                        $.each(error.body.errors, function (index, value) {
                            $('.invalid' + value.field).text(value.message);
                        });
                    })
                } else {
                    this.$resource('/api/v1/profit{/id}').save({}, profit)
                        .then(response => {
                                $('#modalProfit').modal('hide');
                                this.payments.splice(0, this.payments.length);
                                this.refresh();
                            }
                        )
                        .catch(error => {
                            console.log(error);
                            $.each(error.body.errors, function (index, value) {
                                $('.invalid' + value.field).text(value.message);
                            });
                        });
                    this.name = '';
                    this.moneyCount = '';
                }
            },


            saveLoss: function () {
                var loss = {walletId: this.walletId, name: this.name, moneyCount: this.moneyCount};

                if (this.id) {
                    this.$resource('/api/v1/loss{/id}').update({id: this.goalId}, loss).then(result =>
                        result.json().then(data => {
                            this.name = '';
                            this.moneyCount = '';
                            this.id = '';
                            $('#modalLoss').modal('hide');
                            this.payments.splice(0, this.payments.length);
                            this.refresh();
                        })
                    ).catch(error => {
                        $.each(error.body.errors, function (index, value) {
                            $('.invalid' + value.field).text(value.message);
                        });
                    })
                } else {
                    this.$resource('/api/v1/loss{/id}').save({}, loss)
                        .then(response => {
                                $('#modalLoss').modal('hide');
                                this.payments.splice(0, this.payments.length);
                                this.refresh();
                            }
                        )
                        .catch(error => {
                            console.log(error);
                            $.each(error.body.errors, function (index, value) {
                                $('.invalid' + value.field).text(value.message);
                            });
                        });
                    this.name = '';
                    this.moneyCount = '';
                }
            }
        }
    }
</script>

<style scoped>
    .createPayment {
        margin: 20px 20px;
        transition: all 1s;
    }

    .createPayment:hover {
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