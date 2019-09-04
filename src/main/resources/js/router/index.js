import Vue from "vue";
import Router from "vue-router";

Vue.use(Router);

export default new Router({
    mode: "history",
    linkActiveClass: "active",
    linkExactActiveClass: "active",
    routes: [
        {
            path: "/",
            component: () => import("../components/Home.vue")
        },
        {
            path: "/api/v1/wallets",
            component: () => import("../components/Home.vue"),
        },
        {
            path: "/api/v1/wallet/detail/:walletId",
            name: 'Detail',
            component: () => import("../components/Detail.vue"),
            props: true
        },
        {
            path: "/api/v1/report/detail/:reportId",
            name: 'ReportDetail',
            component: () => import("../components/ReportDetail.vue"),
            props: true
        },
        {
            path: "/api/v1/reports",
            name: 'Report',
            component: () => import("../components/Report.vue"),
            props: true
        },
        {
            path: "/api/v1/profile",
            name: 'EditAccount',
            component: () => import("../components/EditAccount.vue"),
            props: true
        },
        {
            path: "*",
            component: () => import("../components/Home.vue")
        }
    ]
});