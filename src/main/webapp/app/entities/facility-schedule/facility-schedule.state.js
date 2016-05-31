(function() {
    'use strict';

    angular
        .module('brmApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('facility-schedule', {
            parent: 'entity',
            url: '/facility-schedule',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FacilitySchedules'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/facility-schedule/facility-schedules.html',
                    controller: 'FacilityScheduleController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('facility-schedule-detail', {
            parent: 'entity',
            url: '/facility-schedule/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'FacilitySchedule'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/facility-schedule/facility-schedule-detail.html',
                    controller: 'FacilityScheduleDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'FacilitySchedule', function($stateParams, FacilitySchedule) {
                    return FacilitySchedule.get({id : $stateParams.id}).$promise;
                }]
            }
        })
        .state('facility-schedule.new', {
            parent: 'facility-schedule',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-schedule/facility-schedule-dialog.html',
                    controller: 'FacilityScheduleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                start: null,
                                end: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('facility-schedule', null, { reload: true });
                }, function() {
                    $state.go('facility-schedule');
                });
            }]
        })
        .state('facility-schedule.edit', {
            parent: 'facility-schedule',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-schedule/facility-schedule-dialog.html',
                    controller: 'FacilityScheduleDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['FacilitySchedule', function(FacilitySchedule) {
                            return FacilitySchedule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facility-schedule', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('facility-schedule.delete', {
            parent: 'facility-schedule',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/facility-schedule/facility-schedule-delete-dialog.html',
                    controller: 'FacilityScheduleDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['FacilitySchedule', function(FacilitySchedule) {
                            return FacilitySchedule.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('facility-schedule', null, { reload: true });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
