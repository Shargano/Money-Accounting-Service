<template>
    <div class="col-md-12 text-center">
        <div class="reportItem">
            <div class="field">From: {{dateFrom}}</div>
            <div class="field">To: {{dateTo}}</div>
            <div class="field">Profits: {{profits.length}}</div>
            <div class="field">Losses: {{losses.length}}</div>
            <div class="field">Limits: {{limits.length}}</div>
            <span class="more">
                <router-link :to="{path: `/api/v1/report/detail/${this.reportId}`}">
                    View details
                </router-link>
            </span>
        </div>
    </div>
</template>

<script>
    import ReportDetail from "components/ReportDetail.vue";

    export default {
        name: "ReportItem",
        props: ['report', 'reports'],
        components: {ReportDetail},
        data() {
            return {
                reportId: this.report.id,
                dateFrom: this.report.dateFrom,
                dateTo: this.report.dateTo,
                profits: this.report.profits,
                losses: this.report.losses,
                limits: this.report.limits
            }
        },
        created: function () {
            this.dateFrom = this.report.dateFrom.split("T")[0];
            this.dateTo = this.report.dateTo.split("T")[0];
        }
    }
</script>

<style scoped>
    .reportItem {
        position: relative;
        margin: 10px auto;
        padding: 20px;
        width: 75%;
        height: 80px;
        border-radius: 8px;
        border: 1px solid #19bd9a;
    }

    .field {
        color: #000;
        margin: 0 20px;
        display: inline-block;
    }

    .more {
        position: absolute;
        bottom: 3px;
        right: 3px;
        font-size: 1rem;
    }
</style>