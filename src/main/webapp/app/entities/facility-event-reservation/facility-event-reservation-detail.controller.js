(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityEventReservationDetailController', FacilityEventReservationDetailController);

    FacilityEventReservationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'FacilityEventReservation', 'Customer', 'FacilityEventServiceReservation', 'FacilityEventSeatingCatalog'];

    function FacilityEventReservationDetailController($scope, $rootScope, $stateParams, entity, FacilityEventReservation, Customer, FacilityEventServiceReservation, FacilityEventSeatingCatalog) {
        var vm = this;

        vm.facilityEventReservation = entity;

        var unsubscribe = $rootScope.$on('brmApp:facilityEventReservationUpdate', function(event, result) {
            vm.facilityEventReservation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
