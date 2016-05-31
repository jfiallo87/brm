(function() {
    'use strict';

    angular
        .module('brmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('service-catalog', {
            parent: 'entity',
            url: '/service-catalog',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ServiceCatalogs'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/service-catalog/service-catalogs.html',
                    controller: 'ServiceCatalogController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('service-catalog-detail', {
            parent: 'entity',
            url: '/service-catalog/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'ServiceCatalog'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/service-catalog/service-catalog-detail.html',
                    controller: 'ServiceCatalogDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'ServiceCatalog', function($stateParams, ServiceCatalog) {
                    return ServiceCatalog.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('service-catalog.new', {
            parent: 'service-catalog',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/service-catalog/service-catalog-dialog.html',
                    controller: 'ServiceCatalogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                name: null,
                                description: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('service-catalog', null, { reload: true });
                }, function() {
                    $state.go('service-catalog');
                });
            }]
        })
        .state('service-catalog.edit', {
            parent: 'service-catalog',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/service-catalog/service-catalog-dialog.html',
                    controller: 'ServiceCatalogDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ServiceCatalog', function(ServiceCatalog) {
                            return ServiceCatalog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('service-catalog', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('service-catalog.delete', {
            parent: 'service-catalog',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/service-catalog/service-catalog-delete-dialog.html',
                    controller: 'ServiceCatalogDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ServiceCatalog', function(ServiceCatalog) {
                            return ServiceCatalog.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('service-catalog', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
