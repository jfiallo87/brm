(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityEventServiceReservationDeleteController',FacilityEventServiceReservationDeleteController);

    FacilityEventServiceReservationDeleteController.$inject = ['$uibModalInstance', 'entity', 'FacilityEventServiceReservation'];

    function FacilityEventServiceReservationDeleteController($uibModalInstance, entity, FacilityEventServiceReservation) {
        var vm = this;

        vm.facilityEventServiceReservation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FacilityEventServiceReservation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
