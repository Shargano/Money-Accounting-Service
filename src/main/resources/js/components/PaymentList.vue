<template>
    <div class="paymentList">
        <PaymentForm :payments="payments" :walletId="walletId" :paymentAttr="payment"></PaymentForm>
        <div v-if="payments.length === 0">
            <h3>You can create new operation right now!</h3>
        </div>
        <div v-else>
            <div class="tableInfo">
                <ul>
                    <li><span style="background-color: rgba(0, 147, 0, 0.66);"></span>Profit</li>
                    <li><span style="background-color: rgba(152, 0, 0, 0.8);"></span>Loss</li>
                    <li><span style="background-color: rgba(124, 111, 107, 0.78);"></span>Transfer</li>
                </ul>
            </div>

            <table class="paymentTable" cellspacing="2" border="1" cellpadding="5">
                <tr>
                    <!--                <th>id</th>-->
                    <!--                <th>Type</th>-->
                    <!--                <th>Id of Operation</th>-->
                    <th>Date time</th>
                    <th>Money count</th>
                    <th>Button Edit</th>
                    <th>Button delete</th>
                </tr>
                <PaymentItem v-for="payment in payments" :key="payment.id" :payments="payments" :payment="payment"
                             :editMethod="editMethod"></PaymentItem>
            </table>
        </div>
    </div>
</template>

<script>
    import PaymentItem from 'components/PaymentItem.vue'
    import PaymentForm from 'components/PaymentForm.vue'

    export default {
        name: "PaymentList",
        components: {PaymentItem, PaymentForm},
        props: ['payments', 'walletId'],
        data: function () {
            return {
                payment: null
            }
        },
        methods: {
            editMethod: function (payment) {
                this.payment = payment;
            }
        },
        created: function () {
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
        }
    }
</script>

<style scoped>
    .tableInfo ul {
        list-style: none;
    }

    .tableInfo ul li {
        display: inline;
        margin: 0 20px;
    }

    .tableInfo ul li span {
        margin-right: 5px;
        display: inline-block;
        height: 10px;
        width: 10px;
        -webkit-border-radius: 50%;
        -moz-border-radius: 50%;
        border-radius: 50%;
        background-color: #000;
    }

    .paymentList {
        color: #000;
    }

    table {
        margin: 0 auto;
    }
</style>