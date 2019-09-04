<template>
    <span>
        <Menu></Menu>
        <div class="container-fluid wrapper">
            <div class="row">
                <div class="col-md-1"></div>
                <div class="col-md-10">
                    <div class="container-fluid">
                        <div class="row">
                            <div class="col-md-3 text-center">
                                <h3>Limits</h3>
                                <div v-if="limits.length === 0" class="text-center">
                                    <h4>None</h4>
                                </div>
                                <div v-else="">
                                    <LimitDetail v-for="limit in limits" :key="limit.id" :limit="limit"></LimitDetail>
                                </div>
                            </div>
                            <div class="col-md-1"></div>
                            <div class="col-md-3 text-center">
                                <h3>Profits</h3>
                                <div v-if="profits.length === 0" class="text-center">
                                    <h4>None</h4>
                                </div>
                                <div v-else>
                                    <ProfitDetail v-for="profit in profits" :key="profit.id"
                                                  :profit="profit"></ProfitDetail>
                                </div>
                            </div>
                            <div class="col-md-1"></div>
                            <div class="col-md-3 text-center">
                                <h3>Losses</h3>
                                <div v-if="losses.length === 0" class="text-center">
                                    <h4>None</h4>
                                </div>
                                <div v-else>
                                    <LossDetail v-for="loss in losses" :key="loss.id" :loss="loss"></LossDetail>
                                </div>
                            </div>
                            <div class="col-md-1"></div>
                        </div>
                    </div>
                </div>
                <div class="col-md-1"></div>
            </div>
        </div>
    </span>
</template>

<script>
    import Menu from "components/Menu.vue";
    import LimitDetail from "components/LimitDetail.vue";
    import ProfitDetail from "components/ProfitDetail.vue";
    import LossDetail from "components/LossDetail.vue";

    export default {
        name: "ReportDetail",
        components: {Menu, LimitDetail, ProfitDetail, LossDetail},
        data() {
            return {
                reportId: this.$route.params.reportId,
                losses: [],
                profits: [],
                limits: []
            }
        },
        created: function () {
            this.$resource('/api/v1/report{/id}').get({id: this.reportId}).then(response =>
                response.json().then(data => {
                    data.limits.forEach(lim => {
                            lim.dateFrom = lim.dateFrom.split("T")[0];
                            lim.dateTo = lim.dateTo.split("T")[0];
                            this.limits.push(lim);
                        }
                    );

                    data.profits.forEach(profit => {
                            profit.dateTime = profit.dateTime.split("T")[0] + ' '
                                + profit.dateTime.split("T")[1].split("+")[0].split(":")[0] + ':'
                                + profit.dateTime.split("T")[1].split("+")[0].split(":")[1];
                            this.profits.push(profit)
                        }
                    );

                    data.losses.forEach(loss => {
                            loss.dateTime = loss.dateTime.split("T")[0] + ' '
                                + loss.dateTime.split("T")[1].split("+")[0].split(":")[0] + ':'
                                + loss.dateTime.split("T")[1].split("+")[0].split(":")[1];
                            this.losses.push(loss)
                        }
                    );
                })
            )
        }
    }
</script>

<style scoped>
    .wrapper {
        margin-top: 15vh;
        color: #000;
    }
</style>