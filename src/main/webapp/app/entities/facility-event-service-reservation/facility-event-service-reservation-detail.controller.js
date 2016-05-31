(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityEventServiceReservationDetailController', FacilityEventServiceReservationDetailController);

    FacilityEventServiceReservationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'entity', 'FacilityEventServiceReservation', 'FacilityEventReservation', 'FacilityEventServiceCatalog'];

    function FacilityEventServiceReservationDetailController($scope, $rootScope, $stateParams, entity, FacilityEventServiceReservation, FacilityEventReservation, FacilityEventServiceCatalog) {
        var vm = this;

        vm.facilityEventServiceReservation = entity;

        var unsubscribe = $rootScope.$on('brmApp:facilityEventServiceReservationUpdate', function(event, result) {
            vm.facilityEventServiceReservation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
