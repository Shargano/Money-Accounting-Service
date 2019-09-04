<template>
    <div class="col-md-12 text-center">
        <button type="button" @click="show" class="addReportItem">
            <h4>Get new report</h4>
        </button>

        <div class="modal fade" id="modalReport" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">Report</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body text-center">
                        <div class="invalid invaliddateFrom"></div>
                        <input type="date" placeholder="Enter date from" v-model="dateFrom"><br>
                        <div class="invalid invaliddateTo"></div>
                        <input type="date" placeholder="Enter date to" v-model="dateTo"><br>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary closeButton" data-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary saveButton" @click="save">Save</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "ReportForm",
        props: ['reports'],
        data: function () {
            return {
                id: '',
                dateFrom: '',
                dateTo: ''
            }
        },
        methods: {
            show: function () {
                $('.invaliddateFrom').text('');
                $('.invaliddateTo').text('');
                $('#modalReport').modal('show');
            },
            save: function () {
                var request = {dateFrom: this.dateFrom, dateTo: this.dateTo};

                this.$resource('/api/v1/report').save({}, request).then(result =>
                    result.json().then(data => {
                        this.reports.push(data);
                        this.dateTo = '';
                        this.dateFrom = '';
                        $('#modalReport').modal('hide');
                    })
                ).catch(error => {
                    $.each(error.body.errors, function (index, value) {
                        $('.invalid' + value.field).text(value.message);
                    });
                })
            }
        }
    }
</script>

<style scoped>
    .addReportItem {
        margin: 10px;
        background-color: #19bd9a;
        min-width: 75%;
        height: 80px;
        border-radius: 8px;
        border: 1px solid #19bd9a;
    }

    .addReportItem h4 {
        color: #fff;
    }

    #modalReport .modal-title {
        color: #000;
    }

    #modalReport input {
        margin-bottom: 10px;
        border-radius: 5px;
        border: 1px solid silver;
    }

    #modalReport input:active {
        margin-bottom: 10px;
        border-radius: 5px;
        border: 1px solid #19bd9a;
    }

    #modalReport .closeButton {
        margin: 0 auto;
        background-color: darkred;
        color: #fff;
    }

    #modalReport .saveButton {
        margin: 0 auto;
        background-color: #19bd9a;
        color: #fff;
    }
</style>