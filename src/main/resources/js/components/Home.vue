<template>
    <span>
        <Menu></Menu>
        <span v-if="!userName">
            <HomePage></HomePage>
        </span>
        <span v-else>
            <WalletsList :wallets="wallets"></WalletsList>
        </span>
    </span>
</template>

<script>
    import Menu from "components/Menu.vue";
    import HomePage from "components/HomePage.vue";
    import WalletsList from "components/WalletsList.vue";
    import EditAccount from "components/EditAccount.vue";
    import Report from "components/Report.vue";

    export default {
        name: "Home",
        data() {
            return {
                userName: frontendData.userName,
                isEnable: frontendData.isEnable,
                wallets: []
            }
        },
        components: {HomePage, Menu, WalletsList, EditAccount, Report}
    }
</script>

<style scoped>

</style>