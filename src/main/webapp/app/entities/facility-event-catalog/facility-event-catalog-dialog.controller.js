(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('FacilityEventCatalogDialogController', FacilityEventCatalogDialogController);

    FacilityEventCatalogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'FacilityEventCatalog', 'FacilityCatalog', 'EventCatalog', 'FacilityEventSeatingCatalog', 'FacilityEventServiceCatalog', 'FacilityEventSchedule'];

    function FacilityEventCatalogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, FacilityEventCatalog, FacilityCatalog, EventCatalog, FacilityEventSeatingCatalog, FacilityEventServiceCatalog, FacilityEventSchedule) {
        var vm = this;

        vm.facilityEventCatalog = entity;
        vm.clear = clear;
        vm.save = save;
        vm.facilitycatalogs = FacilityCatalog.query();
        vm.eventcatalogs = EventCatalog.query();
        vm.facilityeventseatingcatalogs = FacilityEventSeatingCatalog.query();
        vm.facilityeventservicecatalogs = FacilityEventServiceCatalog.query();
        vm.facilityeventschedules = FacilityEventSchedule.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.facilityEventCatalog.id !== null) {
                FacilityEventCatalog.update(vm.facilityEventCatalog, onSaveSuccess, onSaveError);
            } else {
                FacilityEventCatalog.save(vm.facilityEventCatalog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('brmApp:facilityEventCatalogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
