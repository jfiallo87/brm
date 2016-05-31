(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityEventReservationDeleteController',FacilityEventReservationDeleteController);

    FacilityEventReservationDeleteController.$inject = ['$uibModalInstance', 'entity', 'FacilityEventReservation'];

    function FacilityEventReservationDeleteController($uibModalInstance, entity, FacilityEventReservation) {
        var vm = this;

        vm.facilityEventReservation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FacilityEventReservation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
