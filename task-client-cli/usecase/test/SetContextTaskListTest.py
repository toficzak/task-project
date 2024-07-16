import os
from unittest import TestCase
from unittest.mock import Mock

from cache_wrapper.Data import Data
from usecase.SetContextTaskListUseCase import SetContextTaskListUseCase
from usecase.cacher.TaskListsCacher import TaskListsCacher

result_file_name = "./cache_context_task_list.csv"


def remove_cached_task_lists_file():
    os.remove("./cache_task_lists.csv")


def remove_cached_context_task_list_file():
    os.remove(result_file_name)


class Test(TestCase):
    def test_set_context_task_list(self):
        # given:
        TaskListsCacher().get_cacher().clear_file_then_persist(Data("1", "991", "name"))

        args = Mock()
        args.alias_task_list_id = "1"

        # when:
        SetContextTaskListUseCase(args).set_context_task_list()

        # then:

        assert os.path.isfile(result_file_name) is True
        with open(result_file_name, "r") as file:
            for line in file.readlines():
                assert line == "991@@name\n"

        remove_cached_context_task_list_file()
        remove_cached_task_lists_file()
