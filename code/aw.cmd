@echo off
call checkProps4Win.cmd
rmdir /s /q target
mkdir target

rem Build application for Windows.
call baw.cmd

rem Build checksum files for Windows.
call bcw.cmd
