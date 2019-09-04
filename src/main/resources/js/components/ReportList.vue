<template>
    <div class="walletList text-center">
        <h2>Reports</h2>
        <div class="container">
            <div class="row">
                <ReportForm :reports="reports"></ReportForm>
                <ReportItem v-for="report in reports" :key="report.id" :reports="reports" :report="report"></ReportItem>
            </div>
        </div>
    </div>
</template>

<script>
    import ReportItem from "components/ReportItem.vue";
    import ReportForm from "components/ReportForm.vue";

    export default {
        name: "ReportList",
        props: ['reports'],
        components: {ReportItem, ReportForm},
        created: function () {
            this.$resource('/api/v1/report').get().then(result =>
                result.json().then(data =>
                    data.forEach(r => this.reports.push(r))
                )
            )
        }
    }
</script>

<style scoped>
    h2 {
        color: #000;
        margin-top: 30px;
    }
</style>