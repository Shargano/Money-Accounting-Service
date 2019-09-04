<template>
    <span>
        <Menu></Menu>
        <div class="container-fluid">
            <div class="row" style="margin-top: 20vh;">
                <div class="col-md-1"></div>
                <div class="col-md-5 text-center limitList">
                    <h2>List of Limits</h2>
                    <LimitList :limits="limits" :walletId="walletId"></LimitList>
                </div>
                <div class="col-md-5 text-center paymentList">
                    <h2>List of payments</h2>
                    <PaymentList :payments="payments" :walletId="walletId"></PaymentList>
                </div>
                <div class="col-md-1"></div>
            </div>
        </div>
    </span>
</template>

<script>
    import PaymentList from 'components/PaymentList.vue'
    import Menu from "components/Menu.vue";
    import LimitList from "components/LimitList.vue";

    export default {
        name: "Detail",
        components: {PaymentList, Menu, LimitList},
        data() {
            return {
                walletId: this.$route.params.walletId,
                wallet: '',
                payments: [],
                limits: []
            }
        },
        created: function () {
            this.$resource('/api/v1/wallet{/id}').get({id: this.walletId}).then(response =>
                response.json().then(data => {
                    this.wallet = data;
                    data.limits.forEach(lim => {
                            lim.dateFrom = lim.dateFrom.split("T")[0];
                            lim.dateTo = lim.dateTo.split("T")[0];
                            this.limits.push(lim);
                        }
                    )
                })
            )
        }
    }
</script>

<style scoped>
    .limitList, .paymentList {
        color: #000;
    }

</style>