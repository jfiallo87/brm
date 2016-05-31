(function() {
    'use strict';

    angular
        .module('brmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('facility-catalog', {
            parent: 'entity',
            url: '/facility-catalog',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FacilityCatalogs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/facility-catalog/facility-catalogs.html',
                    controller: 'FacilityCatalogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('facility-catalog-detail', {
            parent: 'entity',
            url: '/facility-catalog/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FacilityCatalog'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/facility-catalog/facility-catalog-detail.html',
                    controller: 'FacilityCatalogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'FacilityCatalog', function($stateParams, FacilityCatalog) {
                    return FacilityCatalog.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('facility-catalog.new', {
            parent: 'facility-catalog',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-catalog/facility-catalog-dialog.html',
                    controller: 'FacilityCatalogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                maxCapacity: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('facility-catalog', null, { reload: true });
                }, function() {
                    $state.go('facility-catalog');
                });
            }]
        })
        .state('facility-catalog.edit', {
            parent: 'facility-catalog',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-catalog/facility-catalog-dialog.html',
                    controller: 'FacilityCatalogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FacilityCatalog', function(FacilityCatalog) {
                            return FacilityCatalog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facility-catalog', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('facility-catalog.delete', {
            parent: 'facility-catalog',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-catalog/facility-catalog-delete-dialog.html',
                    controller: 'FacilityCatalogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FacilityCatalog', function(FacilityCatalog) {
                            return FacilityCatalog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facility-catalog', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
