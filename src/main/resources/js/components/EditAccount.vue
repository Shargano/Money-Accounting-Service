<template>
    <span>
        <Menu></Menu>
        <div class="container-fluid formWrapper">
            <div class="row">
                <div class="col-md-3"></div>
                <div class="col-md-6 text-center">
                    <form action="/api/v1/registration" method="post">
                        <div>
                            <label>Time zone: </label><br>
                            <div class="invalid invalidtimeZone"></div>
                            <select v-model="timeZone">
                                <option v-for="zone in zones">{{zone}}</option>
                            </select>
                            <div class="success"></div>
                        </div>
                        <button type="button" @click="edit">Save</button>
                    </form>
                </div>
                <div class="col-md-3"></div>
            </div>
        </div>
    </span>
</template>

<script>
    import Menu from "components/Menu.vue"

    export default {
        name: "EditAccount",
        components: {Menu},
        data: function () {
            return {
                timeZone: '',
                zones: []
            }
        },
        methods: {
            edit: function () {
                var request = {
                    timeZone: this.timeZone
                };
                this.$resource('/api/v1/registration').save({}, request)
                    .then(response => $('.success').text('SUCCESS!'))
                    .catch(error => {
                        $.each(error.body.errors, function (index, value) {
                            $('.invalid' + value.field).text(value.message);
                        });
                    })
            }
        },
        created: function () {
            this.$resource('/api/v1/timezone').get().then(response =>
                response.json().then(data =>
                    data.forEach(w => this.zones.push(w))
                ));
        }
    }
</script>

<style scoped>
    body {
        background-color: red;
    }

    .formWrapper {
        background-color: #1ddfb4;
        -webkit-border-radius: 20px;
        -moz-border-radius: 20px;
        border-radius: 20px;
        color: #555;
        width: 500px;
        min-height: 200px;
        margin: 30vh auto 0 auto;
        padding: 30px 0;
    }

    :active, :hover, :focus {
        outline: 0;
        outline-offset: 0;
    }

    .formWrapper h2 {
        margin: 0;
    }

    select {
        border-radius: 8px;
        height: 37px;
        width: 250px;
        margin-bottom: 17px;
        font-size: 1.25em;
        font-weight: 500;
        line-height: 1.75em;
        transition: 1s ease;
        border: 1px solid black;
    }

    input[type=text], input[type=email], textarea, input[type=password] {
        height: 37px;
        border-radius: 8px;
        padding: 10px;
        margin-bottom: 17px;
        font-size: 1.25em;
        font-weight: 500;
        line-height: 1.75em;
        transition: 1s ease;
        border: 1px solid black;
    }

    input[type=email]:hover, input[type=password]:hover, input[type=text]:hover, textarea:hover, select:hover {
        box-shadow: 0 0 25px rgba(0, 0, 0, 0.5);
        border: 1px solid rgba(0, 0, 0, .9);
    }

    input[type=email]:focus, input[type=password]:focus, input[type=text]:focus, textarea:focus, select:focus {
        box-shadow: 0 0 25px rgba(0, 0, 0, 0.5);
        border: 1px solid rgba(0, 0, 0, .9);
        outline: none;
    }

    input[type=button] {
        margin-top: 10px;
        width: 240px;
        height: 40px;
        background-color: transparent;
        border-radius: 8px;
        font-size: 1.25em;
        line-height: 1.375em;
        text-transform: uppercase;
        transition: 1s ease;
        border: 1px solid black;
    }

    input[type=button]:hover {
        cursor: pointer;
        border-color: #fff;
        background-color: rgba(0, 0, 0, .8);
        color: #fff;
    }

    .formWrapper form a {
        color: #22224f;
    }

    .formWrapper form a:hover {
        color: #000;
    }

    .success {
        color: #fafafa;
        text-transform: uppercase;
    }
</style>