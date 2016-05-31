(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityEventScheduleDialogController', FacilityEventScheduleDialogController);

    FacilityEventScheduleDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FacilityEventSchedule', 'FacilityEventCatalog'];

    function FacilityEventScheduleDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FacilityEventSchedule, FacilityEventCatalog) {
        var vm = this;

        vm.facilityEventSchedule = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.facilityeventcatalogs = FacilityEventCatalog.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.facilityEventSchedule.id !== null) {
                FacilityEventSchedule.update(vm.facilityEventSchedule, onSaveSuccess, onSaveError);
            } else {
                FacilityEventSchedule.save(vm.facilityEventSchedule, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('brmApp:facilityEventScheduleUpdate', result);
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
