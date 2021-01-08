@echo off
echo.
echo.  %1   is the path to the root project dir; e.g. "C:\Users\sebas\WebstormProjects\Culprit"
echo.
echo.  %2   is the absolute path to the modified markdown file; e.g. "C:\Users\sebas\WebstormProjects\Culprit\content\asdf\twix.md"
echo.
@echo on

cd %1
java -cp %1\culprit_2.jar framework.Main %2