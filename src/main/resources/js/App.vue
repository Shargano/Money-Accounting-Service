<template>
    <router-view></router-view>
</template>

<script>
    import Home from "components/Home.vue";

    export default {
        name: 'app',
        components: {
            Home
        }
    }
</script>

<style>
    ::placeholder {
        color: #666
    }

    ::selection {
        background-color: rgba(25, 189, 154, 0.5);
        color: #fff;
    }

    body {
        font-size: 1em;
        min-width: 320px;
        position: relative;
        line-height: 1.65;
        font-family: 'Open Sans', sans-serif;
        overflow-x: hidden;
        color: #81868e;
    }

    body input, textarea {
        border: #666 1px solid;
        outline: none;
    }

    h1, h2 {
        font-family: 'Raleway', sans-serif;
        font-weight: lighter;
    }

    b, strong {
        font-weight: bold;
    }

    a {
        transition: 1s ease;
    }

    button {
        transition: 0.6s ease;
    }

    button {
        width: 140px;
        height: 37px;
        background-color: transparent;
        border: 1px solid #333b46;
        border-radius: 19px;
        color: #333b46;
        line-height: 0.8em;
    }

    .invalid {
        color: darkred;
        font-size: 16px;
        text-align: center;
    }
</style>