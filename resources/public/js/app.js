
function saveURL() {
    var url = $("#url-to-shorten").val();
}

$(document).ready(function() {
    function ViewModel () {
        var self = this;

        this.url = ko.observable("");

        this.onSave = function () {
            this.showSpinner(true);
            this.newFormActive(false);
        };

        this.enableSaveButton = ko.computed(function() {
            return this.url().length >= 5 && !this.showSpinner();
        }, this);

        this.showSpinner = ko.observable(false);
        this.newFormActive = ko.observable(true);

        this.chosenURL = ko.observable(null);

        Sammy(function(){
            this.get('#/i/:id', function(){
                $.get('/i/' + this.params.id, null, self.chosenURL);
            });
        }).run();
    };

    ko.applyBindings(new ViewModel());
});
