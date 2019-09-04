<template>
    <div class="wrapperReportItem">
        <div class="field">From {{dateFrom}}</div>
        <div class="field">To {{dateTo}}</div>
        <div class="field">Limit is <span class="fa fa-usd"></span>{{moneyCount}}</div>
    </div>
</template>

<script>
    export default {
        name: "LimitDetail",
        props: ["limit"],
        data() {
            return {
                id: this.limit.id,
                dateFrom: this.limit.dateFrom,
                dateTo: this.limit.dateTo,
                moneyCount: this.limit.moneyCount
            }
        }
    }
</script>

<style scoped>
    .wrapperReportItem {
        color: #fafafa;
        -webkit-border-radius: 15px;
        -moz-border-radius: 15px;
        border-radius: 15px;
        background-color: #19bd9a;
        margin: 20px;
    }

    .field span {
        margin-right: 3px;
    }
</style>