<template>
    <div class="walletList text-center">
        <h2>Wallets</h2>
        <div class="container">
            <div class="row">
                <Transfer :wallets="wallets"></Transfer>
            </div>
        </div>
        <WalletItem v-for="wallet in wallets" :key="wallet.id" :wallets="wallets" :wallet="wallet"
                    :editMethod="editMethod"></WalletItem>
        <WalletForm :wallets="wallets" :walletAttr="wallet"></WalletForm>
    </div>
</template>

<script>
    import WalletItem from 'components/WalletItem.vue'
    import WalletForm from 'components/WalletForm.vue'
    import Transfer from 'components/Transfer.vue'

    export default {
        name: "WalletsList",
        props: ['wallets'],
        components: {WalletItem, WalletForm, Transfer},
        data() {
            return {
                wallet: null
            }
        },
        methods: {
            editMethod: function (wallet) {
                this.wallet = wallet;
            }
        },
        created: function () {
            this.$resource('/api/v1/wallet').get().then(result =>
                result.json().then(data =>
                    data.forEach(w => this.wallets.push(w))
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

    .walletList {
        margin-top: 15vh;
    }
</style>