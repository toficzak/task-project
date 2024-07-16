import argparse

from http_wrapper.Requester import Requester
from usecase.AddCommentUseCase import AddCommentUseCase
from usecase.AddTaskListUseCase import AddTaskListUseCase
from usecase.AddTaskUseCase import AddTaskUseCase
from usecase.GetContextTaskListUseCase import GetContextTaskListUseCase
from usecase.GetContextTaskUseCase import GetContextTaskUseCase
from usecase.GetTaskListsUseCase import GetTaskListsUseCase
from usecase.GetTasksUseCase import GetTasksUseCase
from usecase.MoveTaskUseCase import MoveTaskUseCase
from usecase.SetContextTaskListUseCase import SetContextTaskListUseCase
from usecase.SetContextTaskUseCase import SetContextTaskUseCase

# work plan:
# file operations module
# http_wrapper module with isolated try (http_wrapper call) catch(exception) if 200 x else y
# in main() registering parsers, all in one big try-catch
# errors send exceptions, caught in general try-catch to print errors and "return"

requester = Requester()


def main():
    parser = argparse.ArgumentParser(description="Task List CLI Application")

    subparsers = parser.add_subparsers(title="Commands", dest="command")

    # 'add' sub-command
    register_add_task_list(subparsers)
    register_add_task(subparsers)
    register_get_task_lists(subparsers)
    register_get_tasks(subparsers)
    register_move_task(subparsers)
    register_set_context_task_list(subparsers)
    register_get_context_task_list(subparsers)
    register_get_context_task(subparsers)
    register_set_context_task(subparsers)
    register_add_comment(subparsers)

    args = parser.parse_args()
    args.func(args)


def register_add_task_list(subparsers):
    add_parser = subparsers.add_parser("add_task_list", help="Add a new task list")
    add_parser.add_argument("task_list_name", help="Name of the task list to add")
    add_parser.set_defaults(func=_register_add_task_list)


def register_get_context_task_list(subparsers):
    add_parser = subparsers.add_parser("get_context_task_list")
    add_parser.set_defaults(func=_register_get_context_task_list)


def register_get_context_task(subparsers):
    add_parser = subparsers.add_parser("get_context_task")
    add_parser.set_defaults(func=_get_context_task)


def register_set_context_task_list(subparsers):
    add_parser = subparsers.add_parser("set_context_task_list")
    add_parser.add_argument("alias_task_list_id")
    add_parser.set_defaults(func=_set_context_task_list)


def register_set_context_task(subparsers):
    add_parser = subparsers.add_parser("set_context_task")
    add_parser.add_argument("alias_task_id")
    add_parser.set_defaults(func=_register_set_context_task)


def register_move_task(subparsers):
    add_parser = subparsers.add_parser("move")
    add_parser.add_argument("task_id")
    add_parser.add_argument("task_list_id")
    add_parser.set_defaults(func=_move_task)


def register_get_tasks(subparsers):
    add_parser = subparsers.add_parser("tasks", help="Show tasks")
    add_parser.add_argument("task_list_id", nargs="?")
    add_parser.set_defaults(func=_register_get_tasks)


def register_get_task_lists(subparsers):
    add_parser = subparsers.add_parser("lists")
    add_parser.set_defaults(func=_register_get_task_lists)


def register_add_task(subparsers):
    add_parser = subparsers.add_parser("add_task", help="Add a new task")
    add_parser.add_argument("task_name", help="Name of the task to add")
    add_parser.add_argument("task_list_id", help="Task list id", nargs='?')
    add_parser.set_defaults(func=_register_add_task)


def register_add_comment(subparsers):
    add_parser = subparsers.add_parser("add_comment", help="Add comment to task")
    add_parser.add_argument("task_id", help="Task id", nargs='?')
    add_parser.add_argument("comment", help="Comment")
    add_parser.set_defaults(func=_register_add_comment)


def _register_get_tasks(args):
    GetTasksUseCase(args, requester).get_tasks()


def _register_set_context_task(args):
    SetContextTaskUseCase(args)


def _register_add_task(args):
    AddTaskUseCase(args, requester).add_task()


def _register_add_task_list(args):
    AddTaskListUseCase(args, requester).add_task_list()


def _register_add_comment(args):
    AddCommentUseCase(args, requester)


def _register_get_task_lists(args):
    GetTaskListsUseCase(Requester()).show_task_lists()


def _register_get_context_task_list():
    GetContextTaskListUseCase().get_context_task_list()


def _get_context_task():
    GetContextTaskUseCase().get_context_task()


def _set_context_task_list(args):
    SetContextTaskListUseCase(args).set_context_task_list()


def _move_task(args, requester):
    MoveTaskUseCase(args, requester).move_task()


if __name__ == "__main__":
    main()
