<template>
    <tr>
        <!--        <td>(<i>{{id}}</i>)</td>-->
        <td><span>{{dateFrom}}</span></td>
        <td><span>{{dateTo}}</span></td>
        <td><span>{{moneyCount}}</span></td>
    </tr>
</template>

<script>
    export default {
        name: "LimitItem",
        props: ['limit', 'limits'],
        data() {
            return {
                id: this.limit.id,
                dateTo: this.limit.dateTo,
                dateFrom: this.limit.dateFrom,
                moneyCount: this.limit.moneyCount
            }
        }
    }
</script>

<style scoped>

</style>