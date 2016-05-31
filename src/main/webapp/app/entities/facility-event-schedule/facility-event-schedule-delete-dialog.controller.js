(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityEventScheduleDeleteController',FacilityEventScheduleDeleteController);

    FacilityEventScheduleDeleteController.$inject = ['$uibModalInstance', 'entity', 'FacilityEventSchedule'];

    function FacilityEventScheduleDeleteController($uibModalInstance, entity, FacilityEventSchedule) {
        var vm = this;

        vm.facilityEventSchedule = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            FacilityEventSchedule.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
