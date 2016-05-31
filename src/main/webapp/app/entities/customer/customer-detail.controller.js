(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('CustomerDetailController', CustomerDetailController);

    CustomerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'Customer', 'FacilityEventReservation'];

    function CustomerDetailController($scope, $rootScope, $stateParams, entity, Customer, FacilityEventReservation) {
        var vm = this;

        vm.customer = entity;

        var unsubscribe = $rootScope.$on('brmApp:customerUpdate', function(event, result) {
            vm.customer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
