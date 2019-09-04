<template>
    <div class="walletItem">
        <h3 class="itemName">{{wallet.name}}</h3>
        <h2 class="itemMoney"><span class="fa fa-usd"></span>{{wallet.moneyCount}}</h2>
        <input type="button" class="itemEditButton" value="Edit" @click="edit">
        <input type="button" class="itemDeleteButton" value="Delete" data-toggle="modal" data-target="#modalDelete">
        <input type="button" value="Details" class="itemDetails" @click="detail"/>


        <div class="modal fade" id="modalDelete" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title text-center" id="exampleModalLabel">Are you sure?</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body text-center">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">Cancel</button>
                        <button type="button" class="btn btn-primary closeButton" @click="del">Delete</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    import Detail from "components/Detail.vue";

    export default {
        name: "WalletItem",
        props: ['wallet', 'editMethod', 'wallets'],
        components: {Detail},
        data: function () {
            return {
                walletId: this.wallet.id
            }
        },
        methods: {
            show: function () {
                $('.invalidname').text('');
                $('.invalidmoneyCount').text('');
                $('#modalForm').modal('show');
            },
            edit: function () {
                this.show();
                this.editMethod(this.wallet);
            },
            del: function () {
                this.$resource('/api/v1/wallet{/id}').remove({id: this.walletId}).then(result => {
                    if (result.ok) {
                        this.wallets.splice(this.wallets.indexOf(this.wallet), 1)
                    }
                    $('#modalDelete').modal('hide')
                })
            },
            detail: function () {
                this.$router.push({path: `/api/v1/wallet/detail/${this.walletId}`})
            }
        }
    }
</script>

<style scoped>
    .walletItem {
        float: left;
        position: relative;
        margin: 37px;
        background-color: #f1f1f1;
        display: block;
        width: 300px;
        height: 250px;
        border-radius: 8px;
        border: 1px solid #19bd9a;
    }

    .walletItem .itemName {
        text-align: center;
        color: #000;
        margin: 15px auto;
    }

    .walletItem .itemMoney {
        text-align: center;
        color: #1b9570;
        margin: 30px auto;
    }

    .walletItem .itemMoney span {
        margin: 5px;
        font-size: 1.7rem;
    }

    .walletItem .itemEditButton {
        text-align: center;
        width: 90px;
        height: 40px;
        background-color: green;
        position: absolute;
        border-radius: 5px;
        color: #fff;
        bottom: 5px;
        right: 5px;
    }

    .walletItem .itemDeleteButton {
        text-align: center;
        width: 90px;
        height: 40px;
        background-color: darkred;
        position: absolute;
        border-radius: 5px;
        color: #fff;
        bottom: 5px;
        left: 5px;
    }

    .walletItem .itemDetails {
        text-align: center;
        display: block;
        width: 90px;
        height: 40px;
        background-color: darkblue;
        position: absolute;
        border-radius: 5px;
        border: 1px solid darkblue;
        color: #fff;
        bottom: 5px;
        left: 105px;
    }

    .walletItem .itemDetails:hover {
        text-decoration: none;
        text-align: center;
        display: block;
        width: 90px;
        height: 40px;
        background-color: darkblue;
        border: 1px solid darkblue;
        position: absolute;
        border-radius: 5px;
        color: #fff;
        bottom: 5px;
        left: 105px;
    }

    #modalDelete .modal-title {
        color: #000;
    }

    #modalDelete .closeButton {
        margin: 0 auto;
        background-color: darkred;
        color: #fff;
    }
</style>