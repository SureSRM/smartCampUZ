angular.module('smartCampUZApp')

    .controller('feedbackCtrl', ['$scope', 'feedback', 'workers', function ($scope, feedback, workers) {

        // show the error message
        var showError = function (error) {
            $scope.errorMsg = error;
            $scope.error = true;
        };

        // show the success message
        var showSuccess = function (message) {
            $scope.successMsg = message;
            $scope.success = true;
        };

        $scope.workerList = workers.getWorkersName();
        $scope.selectedWorker = $scope.feedback.worker;
        $scope.currentState = $scope.feedback.state;
        $scope.changeState = function (state) {
            $scope.currentState = state;
            if ($scope.currentState == 'Aprobado' || $scope.currentState == 'Denegado'
                || $scope.currentState == 'Notificado') {
                $scope.selectedWorker = "";
            }
            if ($scope.currentState != 'Asignado') {
                var tmpState = {
                    id: $scope.feedback.id,
                    state: $scope.currentState
                };
                feedback.setState(tmpState,showSuccess,showError);
            }
        };
        $scope.assignWorker = function () {
            if ($scope.currentState == 'Asignado') {
                var tmpState = {
                    id: $scope.feedback.id,
                    worker: workers.getWorkerId($scope.selectedWorker)
                };
                workers.assignWorker(tmpState,showSuccess,showError);
            }
        }
    }]);