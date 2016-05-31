(function() {
    'use strict';

    angular
        .module('brmApp')
        .controller('SeatingCatalogDeleteController',SeatingCatalogDeleteController);

    SeatingCatalogDeleteController.$inject = ['$uibModalInstance', 'entity', 'SeatingCatalog'];

    function SeatingCatalogDeleteController($uibModalInstance, entity, SeatingCatalog) {
        var vm = this;

        vm.seatingCatalog = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;
        
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            SeatingCatalog.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
