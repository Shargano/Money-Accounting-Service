<template>
    <div class="limitList">
        <LimitForm :limits="limits" :walletId="walletId"></LimitForm>
        <div v-if="limits.length === 0">
            <h3>You can create new limit right now!</h3>
        </div>
        <div v-else>
            <table class="limitTable" cellspacing="2" border="1" cellpadding="5">
                <tr>
                    <!--                <th>id</th>-->
                    <th>From</th>
                    <th>To</th>
                    <th>Money count</th>
                </tr>
                <LimitItem v-for="limit in limits" :key="limit.id" :limits="limits" :limit="limit"></LimitItem>
            </table>
        </div>
    </div>
</template>

<script>
    import LimitForm from 'components/LimitForm.vue'
    import LimitItem from 'components/LimitItem.vue'

    export default {
        name: "LimitList",
        props: ['limits', 'walletId'],
        components: {LimitItem, LimitForm},
        data: function () {
            return {
                limit: null
            }
        }
    }
</script>

<style scoped>
    table {
        margin: 0 auto;
    }
</style>