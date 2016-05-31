'use strict';

describe('Controller Tests', function() {

    describe('EventCatalog Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockEventCatalog, MockFacilityEventCatalog;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockEventCatalog = jasmine.createSpy('MockEventCatalog');
            MockFacilityEventCatalog = jasmine.createSpy('MockFacilityEventCatalog');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'EventCatalog': MockEventCatalog,
                'FacilityEventCatalog': MockFacilityEventCatalog
            };
            createController = function() {
                $injector.get('$controller')("EventCatalogDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'brmApp:eventCatalogUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
