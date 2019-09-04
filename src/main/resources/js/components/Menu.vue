<template>
    <div class="fixedMenu">
        <div class="topLine">
            <div class="container-fluid">
                <div class="row">
                    <div class="col-md-1"></div>
                    <div class="MainLogo col-md-5">
                        <span>Money Finance Service</span>
                    </div>
                    <div class="col-md-5">
                        <nav class="text-right">
                            <ul v-if="!userName">
                                <li><a href="#" class="disable">Features</a></li>
                                <li><a href="#" class="disable">About us</a></li>
                                <li><a href="/login">Login</a></li>
                            </ul>
                            <ul v-else>
                                <li>
                                    <router-link :to="{path: `/api/v1/reports`}">Reports</router-link>
                                </li>
                                <li>
                                    <router-link :to="{path: `/api/v1/profile`}">Edit profile</router-link>
                                </li>
                                <li>
                                    <router-link :to="{path: `/api/v1/wallets`}">Wallets</router-link>
                                </li>
                                <li><a href="/api/v1/logout">Logout </a></li>
                                <li> {{userName}}</li>
                            </ul>
                        </nav>
                    </div>
                    <div class="col-md-1"></div>
                </div>
            </div>
        </div>
    </div>
</template>
<script>
    export default {
        name: "Menu",
        data() {
            return {
                userName: frontendData.userName
            }
        }
    }
</script>

<style scoped>
    .fixedMenu {
        background: #fff;
        box-shadow: 1px 1px 5px #0003;
        border-bottom: 1px solid #ececec;
        padding: 15px 0 20px 0;
        position: fixed;
        top: 0;
        width: 100%;
        z-index: 5;
    }

    .fixedMenu .topLine {
        height: 35px;
        width: 100%;
    }

    .fixedMenu .topLine .MainLogo span {
        font-family: "Raleway", sans-serif;
        font-weight: bold;
        font-size: 27px;
        letter-spacing: 3px;
        color: #232323;
        text-transform: uppercase;
    }

    .fixedMenu .topLine nav ul {
        padding-top: 5px;
        list-style: none;
    }

    .fixedMenu .topLine nav ul li {
        display: inline-block;
    }

    .fixedMenu .topLine nav ul li a {
        display: block;
        padding: 8px 14px;
        text-decoration: none;
        color: #232323;
        line-height: 0.8em;
        border: 1px solid transparent;
    }

    .fixedMenu .topLine nav ul li a:hover {
        color: #19bd9a;
        border: 1px solid #19bd9a;
        border-radius: 17px;
    }

    .fixedMenu .topLine nav ul li a.disable:hover {
        color: #626865;
        border: 1px solid #626865;
        border-radius: 17px;
    }

    a.disable {
        /*pointer-events: none;*/
        cursor: default;
    }
</style>