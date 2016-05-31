(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('EventCatalogDialogController', EventCatalogDialogController);

    EventCatalogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EventCatalog', 'FacilityEventCatalog'];

    function EventCatalogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EventCatalog, FacilityEventCatalog) {
        var vm = this;

        vm.eventCatalog = entity;
        vm.clear = clear;
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
            if (vm.eventCatalog.id !== null) {
                EventCatalog.update(vm.eventCatalog, onSaveSuccess, onSaveError);
            } else {
                EventCatalog.save(vm.eventCatalog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('brmApp:eventCatalogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
