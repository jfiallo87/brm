(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityEventSeatingCatalogDialogController', FacilityEventSeatingCatalogDialogController);

    FacilityEventSeatingCatalogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FacilityEventSeatingCatalog', 'SeatingCatalog', 'FacilityEventCatalog', 'FacilityEventReservation'];

    function FacilityEventSeatingCatalogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FacilityEventSeatingCatalog, SeatingCatalog, FacilityEventCatalog, FacilityEventReservation) {
        var vm = this;

        vm.facilityEventSeatingCatalog = entity;
        vm.clear = clear;
        vm.save = save;
        vm.seatingcatalogs = SeatingCatalog.query();
        vm.facilityeventcatalogs = FacilityEventCatalog.query();
        vm.facilityeventreservations = FacilityEventReservation.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.facilityEventSeatingCatalog.id !== null) {
                FacilityEventSeatingCatalog.update(vm.facilityEventSeatingCatalog, onSaveSuccess, onSaveError);
            } else {
                FacilityEventSeatingCatalog.save(vm.facilityEventSeatingCatalog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('brmApp:facilityEventSeatingCatalogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
