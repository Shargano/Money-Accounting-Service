<template>
    <div>
        <header>
            <div class="container-fluid">
                <div class="row">
                    <div class="col-8"></div>
                    <div class="col-4">
                        <div class="FirstScreenInfo text-center">
                            <h1>Accounting money</h1>
                            <p>Our service provide to users many opportunities to track their money</p>
                        </div>
                    </div>
                </div>
            </div>
        </header>
    </div>
</template>

<script>
    export default {
        name: "HomePage"
    }
</script>

<style scoped>

    header {
        margin-top: 10vh;
        min-height: 90vh;
        position: relative;
        color: white;
    }

    header .FirstScreenInfo {
        padding-top: 100px;
    }

    header .FirstScreenInfo h1 {
        font-size: 2.2em;
        line-height: 2.5em;
        text-transform: uppercase;
        letter-spacing: 2px;
    }

    header .FirstScreenInfo p {
        font-size: 1.7em;
        line-height: 1.75em;
        margin-left: 10%;
        margin-top: 70px;
        width: 80%;
    }


    /********************About us**********************/
    #About {
        padding: 30px 0;
    }

    #About h2 {
        color: #232323;
        letter-spacing: 2px;
        font-family: "Raleway", sans-serif;
        font-size: 20px;
        margin-bottom: 10px;
        text-align: center;
        font-weight: bold;
        text-transform: uppercase;
    }

    #About p {
        color: #232323;
        letter-spacing: 1px;
        font-family: "Open Sans", sans-serif;
        font-size: 16px;
        margin-bottom: 40px;
        text-align: center;
    }

    #About .member {
        padding: 30px 10px;
        background-color: #e7e7e7;
        margin-bottom: 40px;
    }

    #About .member h3 {
        text-align: center;
        color: #141414;

    }

    #About .member p {

    }

    #About .member img {
        width: 350px;
        height: 300px;
    }
</style>