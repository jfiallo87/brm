(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityEventReservationDialogController', FacilityEventReservationDialogController);

    FacilityEventReservationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FacilityEventReservation', 'Customer', 'FacilityEventServiceReservation', 'FacilityEventSeatingCatalog'];

    function FacilityEventReservationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FacilityEventReservation, Customer, FacilityEventServiceReservation, FacilityEventSeatingCatalog) {
        var vm = this;

        vm.facilityEventReservation = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.customers = Customer.query();
        vm.facilityeventservicereservations = FacilityEventServiceReservation.query();
        vm.facilityeventseatingcatalogs = FacilityEventSeatingCatalog.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.facilityEventReservation.id !== null) {
                FacilityEventReservation.update(vm.facilityEventReservation, onSaveSuccess, onSaveError);
            } else {
                FacilityEventReservation.save(vm.facilityEventReservation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('brmApp:facilityEventReservationUpdate', result);
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
