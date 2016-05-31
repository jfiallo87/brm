(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityScheduleDialogController', FacilityScheduleDialogController);

    FacilityScheduleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FacilitySchedule', 'FacilityCatalog'];

    function FacilityScheduleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FacilitySchedule, FacilityCatalog) {
        var vm = this;

        vm.facilitySchedule = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.facilitycatalogs = FacilityCatalog.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.facilitySchedule.id !== null) {
                FacilitySchedule.update(vm.facilitySchedule, onSaveSuccess, onSaveError);
            } else {
                FacilitySchedule.save(vm.facilitySchedule, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('brmApp:facilityScheduleUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.start = false;
        vm.datePickerOpenStatus.end = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
