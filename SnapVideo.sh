cd /home/gemelos/Videos/videoJava
ffmpeg -framerate 1/2.5 -pattern_type glob -i '*.jpg' -i audio.mp3 -r 25 -pix_fmt yuv420p video.mp4
