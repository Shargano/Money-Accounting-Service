<template>
    <span>
        <Menu></Menu>
        <ReportList :reports="reports" style="margin-top: 14vh;"></ReportList>
    </span>
</template>

<script>
    import Menu from "components/Menu.vue";
    import ReportList from "components/ReportList.vue";
    import ReportForm from "components/ReportForm.vue";

    export default {
        name: "Report",
        components: {Menu, ReportList, ReportForm},
        data() {
            return {
                reports: []
            }
        },
    }
</script>

<style scoped>

</style>