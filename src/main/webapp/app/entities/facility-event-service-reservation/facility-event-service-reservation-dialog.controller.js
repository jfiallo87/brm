(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityEventServiceReservationDialogController', FacilityEventServiceReservationDialogController);

    FacilityEventServiceReservationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FacilityEventServiceReservation', 'FacilityEventReservation', 'FacilityEventServiceCatalog'];

    function FacilityEventServiceReservationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FacilityEventServiceReservation, FacilityEventReservation, FacilityEventServiceCatalog) {
        var vm = this;

        vm.facilityEventServiceReservation = entity;
        vm.clear = clear;
        vm.save = save;
        vm.facilityeventreservations = FacilityEventReservation.query();
        vm.facilityeventservicecatalogs = FacilityEventServiceCatalog.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.facilityEventServiceReservation.id !== null) {
                FacilityEventServiceReservation.update(vm.facilityEventServiceReservation, onSaveSuccess, onSaveError);
            } else {
                FacilityEventServiceReservation.save(vm.facilityEventServiceReservation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('brmApp:facilityEventServiceReservationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
