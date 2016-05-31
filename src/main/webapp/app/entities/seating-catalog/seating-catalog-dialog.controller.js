(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('SeatingCatalogDialogController', SeatingCatalogDialogController);

    SeatingCatalogDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'SeatingCatalog', 'FacilityEventSeatingCatalog'];

    function SeatingCatalogDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, SeatingCatalog, FacilityEventSeatingCatalog) {
        var vm = this;

        vm.seatingCatalog = entity;
        vm.clear = clear;
        vm.save = save;
        vm.facilityeventseatingcatalogs = FacilityEventSeatingCatalog.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.seatingCatalog.id !== null) {
                SeatingCatalog.update(vm.seatingCatalog, onSaveSuccess, onSaveError);
            } else {
                SeatingCatalog.save(vm.seatingCatalog, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('brmApp:seatingCatalogUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
