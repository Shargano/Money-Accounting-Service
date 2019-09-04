<template>
    <div class="wrapperReportItem">
        <div class="field">Name: {{name}}</div>
        <div class="field">Amount: <span class="fa fa-usd"></span>{{moneyCount}}</div>
        <div class="field">At {{dateTime}}</div>
    </div>
</template>

<script>
    export default {
        name: "ProfitDetail",
        props: ["profit"],
        data() {
            return {
                id: this.profit.id,
                name: this.profit.name,
                dateTime: this.profit.dateTime,
                moneyCount: this.profit.moneyCount
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