import unittest
from unittest.mock import Mock

from http_wrapper.Response import Response
from usecase.GetTasksUseCase import GetTasksUseCase


class MyTestCase(unittest.TestCase):
    def test_get_task(self):
        # given
        json = [{
            "id": "420",
            "name": "test_name"
        }]
        mock_requester = Mock()
        mock_requester.get.return_value = Response(200, json)

        args = Mock()
        args.task_list_id = "1"

        # when
        response = GetTasksUseCase(requester=mock_requester, args=args).get_tasks()

        # then
        # todo: why it does not work?
        # assert response[0] == Data("1", "420", "test_name")
        assert response[0].key == "1"
        assert response[0].value == "420"
        assert response[0].name == "test_name"


if __name__ == '__main__':
    unittest.main()
