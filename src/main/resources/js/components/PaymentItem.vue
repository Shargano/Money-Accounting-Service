<template>
    <tr v-bind:class="isProfit ? 'backGreen' : (isLoss ? 'backRed' : 'backBlue')">
        <!--        <td>(<i>{{payment.id}}</i>)</td>-->
        <!--        <td><span>{{payment.type}}</span></td>-->
        <!--        <td><span>{{payment.goalId}}</span></td>-->
        <td><span>{{payment.dateTime}}</span></td>
        <td><span>{{payment.moneyCount}}</span></td>
        <td><input type="button" value="Edit" @click="edit"></td>
        <td><input type="button" value="Delete" @click="del"></td>
    </tr>

</template>

<script>
    export default {
        name: "PaymentItem",
        props: ['payment', 'editMethod', 'payments'],
        data() {
            return {
                id: this.payment.id,
                goalId: this.payment.goalId,
                moneyCount: this.payment.moneyCount,
                type: this.payment.type,
                isProfit: '',
                isLoss: ''
            }
        },
        methods: {
            edit: function () {
                if (this.type == "CREATE_PROFIT" || this.payment.type == "EDIT_PROFIT")
                    $('#modalProfit').modal('show');
                else if (this.type == "CREATE_LOSS" || this.type == "EDIT_LOSS")
                    $('#modalLoss').modal('show');
                this.editMethod(this.payment);
            },
            del: function () {
                if (this.payment.type == "CREATE_PROFIT" || this.payment.type == "EDIT_PROFIT") {
                    this.$resource('/api/v1/profit{/id}').remove({id: this.payment.goalId});
                    this.payments.splice(this.payments.indexOf(this.payment), 1)
                } else if (this.payment.type == "CREATE_LOSS" || this.payment.type == "EDIT_LOSS") {
                    this.$resource('/api/v1/loss{/id}').remove({id: this.payment.goalId});
                    this.payments.splice(this.payments.indexOf(this.payment), 1)
                }
            }
        },
        created: function () {
            if (this.type === "CREATE_PROFIT" || this.type === "EDIT_PROFIT")
                this.isProfit = true;
            else if (this.type === "CREATE_LOSS" || this.type === "EDIT_LOSS")
                this.isLoss = true;
        }
    }
</script>

<style scoped>
    tr {
        color: #fff;
    }

    .backRed {
        background-color: rgba(152, 0, 0, 0.8);
    }

    .backGreen {
        background-color: rgba(0, 147, 0, 0.66);
    }

    .backBlue {
        background-color: rgba(124, 111, 107, 0.78);
    }
</style>