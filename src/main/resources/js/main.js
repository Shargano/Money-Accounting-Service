import Vue from 'vue'
import App from './App.vue'
import router from "./router";
import VueResource from 'vue-resource'

Vue.use(VueResource);

new Vue({
    el: '#app',
    router,
    data: {
        userName: frontendData.userName
    },
    render: a => a(App)
});