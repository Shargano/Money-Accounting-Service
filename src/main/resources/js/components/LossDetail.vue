<template>
    <div class="wrapperReportItem">
        <div class="field">Name: {{name}}</div>
        <div class="field">Amount: <span class="fa fa-usd"></span>{{moneyCount}}</div>
        <div class="field">At {{dateTime}}</div>
        <tr></tr>
    </div>
</template>

<script>
    export default {
        name: "LossDetail",
        props: ["loss"],
        data() {
            return {
                id: this.loss.id,
                name: this.loss.name,
                dateTime: this.loss.dateTime,
                moneyCount: this.loss.moneyCount
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