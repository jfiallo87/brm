'use strict';

describe('Controller Tests', function() {

    describe('FacilityEventSchedule Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockFacilityEventSchedule, MockFacilityEventCatalog;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockFacilityEventSchedule = jasmine.createSpy('MockFacilityEventSchedule');
            MockFacilityEventCatalog = jasmine.createSpy('MockFacilityEventCatalog');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'FacilityEventSchedule': MockFacilityEventSchedule,
                'FacilityEventCatalog': MockFacilityEventCatalog
            };
            createController = function() {
                $injector.get('$controller')("FacilityEventScheduleDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'brmApp:facilityEventScheduleUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
