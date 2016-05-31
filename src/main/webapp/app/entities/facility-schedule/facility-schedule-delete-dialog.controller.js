(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityScheduleDeleteController',FacilityScheduleDeleteController);

    FacilityScheduleDeleteController.$inject = ['$uibModalInstance', 'entity', 'FacilitySchedule'];

    function FacilityScheduleDeleteController($uibModalInstance, entity, FacilitySchedule) {
        var vm = this;

        vm.facilitySchedule = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FacilitySchedule.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
