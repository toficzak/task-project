import unittest
from unittest.mock import Mock

from cache_wrapper.Data import Data
from http_wrapper.Response import Response
from usecase.MoveTaskUseCase import MoveTaskUseCase
from usecase.cacher.TaskListsCacher import TaskListsCacher
from usecase.cacher.TasksCacher import TasksCacher


class MyTestCase(unittest.TestCase):
    def test_move_task(self):
        # given:
        mock_requester = Mock()
        mock_requester.post.return_value = Response(200)

        mock_args = Mock()
        mock_args.alias_task_id = 4
        mock_args.alias_task_list_id = 8

        TasksCacher().get_cacher().clear_file_then_persist(Data("4", "991", "task_name"))
        TaskListsCacher().get_cacher().clear_file_then_persist(Data("8", "434", "task_list_name"))

        # when:
        response = MoveTaskUseCase(
            requester=mock_requester,
            args=mock_args
        ).move_task()

        # then
        assert response.status_code == 200

    @unittest.skip("not implemented yet")
    def test_throw_exception_when_no_such_task(self):
        # given:
        mock_requester = Mock()

        mock_args = Mock()
        mock_args.alias_task_id = 4
        mock_args.alias_task_list_id = 8

        TaskListsCacher().get_cacher().clear_file_then_persist(Data("8", "434", "task_list_name"))

        # when:
        response = MoveTaskUseCase(
            requester=mock_requester,
            args=mock_args
        ).move_task()

        # then
        assert response.status_code == 404

    @unittest.skip("not implemented yet")
    def test_throw_exception_when_no_such_task_list(self):
        # given:
        mock_requester = Mock()

        mock_args = Mock()
        mock_args.alias_task_id = 4
        mock_args.alias_task_list_id = 8

        TasksCacher().get_cacher().clear_file_then_persist(Data("4", "991", "task_name"))

        # when:
        response = MoveTaskUseCase(
            requester=mock_requester,
            args=mock_args
        ).move_task()

        # then
        assert response.status_code == 404


if __name__ == '__main__':
    unittest.main()
